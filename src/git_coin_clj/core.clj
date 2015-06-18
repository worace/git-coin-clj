(ns git-coin-clj.core
  (:require digest
            [clj-http.client :as http]))

(def server-url (or (System/getenv "COIN_SERVER") "http://localhost:9292"))

;; miner components
;; target -- need to store current target as...atom? ref?
;; miner threads -- agents? independent processes looping on
;; digesting target
;; comparing targets -- 1 hash less than another?
;; target refreshing -- another indep thread periodically refreshing target
;; coin verifier -- needs to send messages to server to 

(defn hash [string] (digest/sha-1 string))

(defn hash-num [hash] (read-string (str "0x" hash)))

(defn lower-hash? [hash-one hash-two]
  (apply < (map hash-num [hash-one hash-two])))

