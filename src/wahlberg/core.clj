(ns wahlberg.core
  (:gen-class))

(def invalid-characters
  #"[^a-zA-Z0-9\.\?!\-\s,']")

(defn source-text
  [source-location]
  (slurp source-location))

(defn clean-source-text
  "Removes all the invalid characters from the given source text"
  [source]
  (-> source
    (clojure.string/replace "’" "'")
    (clojure.string/replace invalid-characters " ")
    (clojure.string/replace #"\s+" " ")
    clojure.string/trim))

;
; Main
;
(defn -main
  [& args]
  (println "Hello, World!"))
