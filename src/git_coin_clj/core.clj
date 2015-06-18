(ns git-coin-clj.core
  (:require digest
            [clj-http.client :as http]
            [clojure.core.async :as async]))

;; SPC m e b -- eval buffer
;; SPC m s b -- send and eval buffer in REPL
;; SPC m s B -- send and eval buffer in REPL and switch to REPL in insert mode
(def server-url (or (System/getenv "COIN_SERVER") "http://localhost:9292"))
(def target-url (str server-url "/target"))

(defn fetch-target [] (:body (http/get target-url)))

(def current-target (atom 0))

(defn hash-num [hash] (read-string (str "0x" hash)))

(defn update-target []
  (swap! current-target (fn [_] (hash-num (fetch-target)))))

(defn gen-hash [string] (digest/sha-1 string))


(defn lower-hash? [hash-one hash-two]
  (apply < (map hash-num [hash-one hash-two])))

(defn mine [identifier]
  (loop [message (str ( System/currentTimeMillis ) "-" identifier)
         iterations 0]
    (let [hash (gen-hash message)]
      (println (str "msg: " message))
      (println (str "hash: " hash))
      (if (< iterations 5)
        (recur hash (inc iterations)))
      )
    ))

(mine 1)
