(ns ddns.main
  (:gen-class)
  (:require
   [ddns.system :as system]
   [ddns.system.dispatch :as dispatch]))

(set! *warn-on-reflection* true)

(defn -main [& _]
  (let [_ (dispatch/dispatch-system @system/system-config)]
    (println "System running...")))
