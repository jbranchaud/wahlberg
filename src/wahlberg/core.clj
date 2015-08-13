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
    #(let [mapping %1
           prefix (butlast %2)
           word (last %2)
           prefix-map (get mapping prefix)]
       (assoc mapping prefix
         (assoc prefix-map word (+ (get prefix-map word 0) 1))))
    {}
    groups))

; (defn agg-map-sum
;   "Sum the totals of everything in the agg-prefix-map"
;   [agg-map]
;   (reduce
;     #(+ %1 (val %2))
;     0
;     agg-map))

; (defn build-prob-prefix-map
;   "Build the probability mapping of prefixes to words"
;   [agg-map]
;   (let [total (agg-map-sum agg-map)]
;     (reduce
;       #(assoc %1 (key %2) (/ (val %2) total))
;       {}
;       agg-map)))

;
; Main
;
(defn -main
  [& args]
  (println "Hello, World!"))
