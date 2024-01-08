(ns build
  (:require [clojure.tools.build.api :as b]))

(def app 'mjhika/acdc)
(def version (format "0.1.%s" (b/git-count-revs nil)))
(def class-dir "target/classes")
(def uber-file (format "target/%s-%s-standalone.jar" (name app) version))

;; delay to defer side effects (artifact downloads)
(def basis (delay (b/create-basis {:project "deps.edn"})))

(defn clean [_]
  (b/delete {:path "target"}))

(defn uber [_]
  (clean nil)
  (b/copy-dir {:src-dirs ["src"]
               :target-dir class-dir})
  (b/compile-clj {:basis @basis
                  :ns-compile '[ddns.main]
                  :class-dir class-dir})
  (b/uber {:class-dir class-dir
           :uber-file uber-file
           :basis @basis
           :main 'ddns.main}))
