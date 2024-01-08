(ns ddns.system
  (:require
   [aero.core :as aero]
   [babashka.fs :as fs]))

(def system-config
  "This atom acts as the database for the system. See the project's 
  documentation for supported values."
  (let [conf-file "acdc.config.edn"]
    (if (fs/exists? conf-file)
      (atom (aero/read-config (fs/file conf-file)))
      (do (println "System configuration file \"acdc.config.edn\" not found.")
          (System/exit 2)))))

(comment
  @system-config)
