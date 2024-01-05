(ns ddns.ip.interface
  (:require
   [ddns.ip.impl :as ip]))

(defn get-ip
  "This function will get the public IP of the host. Requests are made using DNS
  resolution."
  []
  (ip/get-ip))

(comment
  (time (get-ip))

  (dotimes [n 100]
    (println n ":" (time (get-ip)))))
