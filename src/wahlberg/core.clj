(ns wahlberg.core
  (:gen-class))

;
; Read content from data source (file, http, etc.)
;
(defn get-source
  "Read in the source content from the file with the given name"
  [filename]
  (slurp filename))

;
; Remove all the junk characters from the input
;
(defn filter-input
  "Remove all the junk characters from the input"
  [input]
  (clojure.string/replace input #"" ""))

;
; Tokenize the given string based primarily on whitespace
;
(defn tokenize-words
  "Tokenize the given string based primarily on whitespace"
  [input]
  (clojure.string/split input #"[\s]+"))

;
; Generate a map of word frequencies as they follow one another
;
(defn generate-frequency-map
  "Generate a map of word frequencies as they follow one another"
  [words]
  (reduce
    (fn [freq-map word-group]
      (let [[lead follow] word-group
            lead-map (or (get freq-map lead) {})
            follow-val (or (get lead-map follow) 0)]
        (assoc freq-map lead (assoc lead-map follow (inc follow-val)))))
    {}
    (partition 2 1 (conj words ""))))

;
; Generate a map of word probabilities as they follow one another
;
(defn generate-probability-map
  "Generate a map of word probabilities as they follow one another"
  [freq-map]
  (reduce
    (fn [prob-map [lead follow-map]]
      (let [lead-freq (reduce + (vals follow-map))]
        (assoc prob-map lead (reduce
                               (fn [probs [follow follow-val]]
                                 (assoc probs follow (/ follow-val lead-freq)))
                               {}
                               follow-map))))
    {}
    freq-map))

;
; Determines if the given string ends in punctuation
;
(defn ends-in-punctuation
  "Determines if the given string ends in punctuation"
  [word]
  (= (last word) \.))

;
; Randomly pick the next word based on current word
;
(defn get-next-word
  "Randomly pick the next word based on current word"
  [probability-map word]
  (let [follow-words (get probability-map word)
        rand-num (rand)]
    (if (nil? follow-words)
      "the"
      (loop [rolling-sum 0
             index 0]
        (let [new-sum (+ rolling-sum (get follow-words (nth (keys follow-words) index)))]
          (if (< rand-num new-sum)
            (nth (keys follow-words) index)
            (recur new-sum (inc index))))))))

;
; Generate random text based on a word probability vector
;
(defn generate-text
  "Generate random text based on a word probability vector"
  [probability-map]
  (let [random-word (rand-nth (keys probability-map))]
    (println
      (clojure.string/join " "
        (loop [curr-word random-word
               words (vector)]
          (if (ends-in-punctuation curr-word)
            (conj words curr-word)
            (recur (get-next-word probability-map curr-word) (conj words curr-word))))))))

;
; Main
;
(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (->> "assets/war_and_peace.txt"
       (get-source)
       (filter-input)
       (tokenize-words)
       (generate-frequency-map)
       (generate-probability-map)
       (generate-text)))
