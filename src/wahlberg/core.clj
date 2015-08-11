(ns wahlberg.core
  (:gen-class))

(defn source-text
  [source-location]
  (slurp source-location))

;
; Main
;
(defn -main
  [& args]
  (println "Hello, World!"))
