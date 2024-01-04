(ns ddns.util
  (:require
   [clojure.string :as str])
  (:import
   (java.util Date)
   (java.text SimpleDateFormat)))

(defn get-domain-name [dns-string]
  (->> (str/split dns-string #"\.")
       (take-last 2)
       (str/join ".")))

(defn new-formatted-dt []
  (let [d (Date.)
        sf (SimpleDateFormat. "yy/MM/dd HH:mm")]
    (.format sf d)))
