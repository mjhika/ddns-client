(ns ddns.ip.interface
  (:require
   [ddns.ip.impl :as ip]))

(defn get-ip []
  (ip/get-ip))

(comment
  (time (get-ip))

  (dotimes [n 100]
    (println n ":" (time (get-ip)))))
