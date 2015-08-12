(ns wahlberg.core
  (:gen-class))

(def invalid-characters
  #"[^a-zA-Z0-9\.\?!\-\s,']")

(defn source-text
  [source-location]
  (slurp source-location))

(defn clean-source
  "Removes all the invalid characters from the given source text"
  [source]
  (-> source
    (clojure.string/replace "’" "'")
    (clojure.string/replace invalid-characters " ")
    (clojure.string/replace #"\s+" " ")
    clojure.string/trim))

(defn split-source
  "Splits the source text into tokens"
  [source]
  (clojure.string/split source #" "))

(defn build-agg-prefix-map
  "Build the aggregate mapping of prefixes to words"
  [tokens prefix-length]
  (agg-prefix-map (partition (+ 1 prefix-length) 1 tokens)))

(defn agg-prefix-map
  "Aggregate the prefix to word mapping from the partition groups"
  [groups]
  (reduce
    #(let [prefix (butlast %2)
           word (last %2)
           mapping {prefix word}]
       (assoc %1 mapping (+ (get %1 mapping 0) 1)))
    {}
    groups))

;
; Main
;
(defn -main
  [& args]
  (println "Hello, World!"))
