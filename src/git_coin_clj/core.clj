(ns git-coin-clj.core
  (:require digest
            [clj-http.client :as http]
            [clojure.core.async :as async]))

;; SPC m e b -- eval buffer
;; SPC m s b -- send and eval buffer in REPL
;; SPC m s B -- send and eval buffer in REPL and switch to REPL in insert mode
(def server-url (or (System/getenv "COIN_SERVER") "http://localhost:9292"))
(def miner-name (or (System/getenv "MINER_NAME") "worace"))
(def target-url (str server-url "/target"))
(def send-coin-url (str server-url "/hash"))

;; miners will send notifs to this channel when they find a coin
(def coin-notifs (async/chan 10))

(defn fetch-target [] (:body (http/get target-url)))

(def current-target (atom 0))

(defn hash-num [hash] (read-string (str "0x" hash)))

(defn update-target! []
  (swap! current-target (fn [_] (hash-num (fetch-target)))))

(defn gen-hash [string] (digest/sha-1 string))


(defn lower-hash? [hash-one hash-two]
  (apply < (map hash-num [hash-one hash-two])))


(defn mine [message]
  (let [hash (gen-hash message)]
    (println (str "msg: " message))
    (println (str "hash: " hash))
    (if (lower-hash? hash @current-target)
      (do
        (println (str "GOT A COIN: " hash))
        (async/>!! coin-notifs {:hash hash :message message}) )))
  hash
)

(defn mine-loop [identifier]
  (loop [message (str ( System/currentTimeMillis ) "-" identifier)
         iterations 0]
    (recur (mine message) (inc iterations))))

(defn send-coin [message name]
  (http/post
   send-coin-url
   {:form-params {"message" message "owner" name}}))

(async/go
  (while true
    (let [coin (async/<! coin-notifs)]
      (println (str "received from chan: "  coin))
      (send-coin (coin :message) "worace")
      )))

(update-target!)
(println @current-target)
(mine "pizza")
(mine "pizza")
(mine "pizza")
(mine "pizza")

