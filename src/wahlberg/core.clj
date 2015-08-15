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

(defn build-agg-prefix-map
  "Build the aggregate mapping of prefixes to words"
  [tokens prefix-length]
  (agg-prefix-map (partition (+ 1 prefix-length) 1 tokens)))

(defn prefix-occurrence
  "Count the number of occurrences of a prefix"
  [prefix agg-map]
  (reduce
    #(+ %1 (val %2))
    0
    (get agg-map prefix)))

(defn next-word
  "grab a random next word"
  [mapping prefix]
  (when (seq (mapping prefix))
    (-> (reduce
              (fn [all [k v]]
                (into all (repeat v k)))
              []
              (mapping prefix))
      rand-nth)))

(defn build-text
  [mapping]
  (clojure.string/join " "
    (map first
      (reductions
        (fn [last-prefix _]
          (vec (rest (conj last-prefix (next-word mapping last-prefix)))))
        (rand-nth (keys mapping))
        (range (+ (rand-int 20) 40))))))

;
; Main
;
(defn -main
  [& args]
  (println
    (-> "assets/war-and-prejudice.txt"
       source-text
       clean-source
       split-source
       (build-agg-prefix-map 3)
       build-text)))
