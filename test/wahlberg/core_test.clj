(ns wahlberg.core-test
  (:require [clojure.test :refer :all]
            [wahlberg.core :refer :all]))

(deftest tokenize-words-test
  (testing "Tokenize words can tokenize based on whitespace"
    (is (tokenize-words "these are some words")
        ["these" "are" "some" "words"])
    (is (tokenize-words "these  are  some    words")
        ["these" "are" "some" "words"])
    (is (tokenize-words "these  are\tsome\nwords")
        ["these" "are" "some" "words"])))

(deftest generate-frequency-map-test
  (testing "Generates a frequency map from the vector of words"
    (is (= (generate-frequency-map ["this" "is" "this" "is" "this" "is" "the" "way" "this" "will" "be"]) { "this" { "is" 3 "will" 1 } "is" { "this" 2 "the" 1 } "the" { "way" 1 } "way" { "this" 1 } "will" { "be" 1 } "be" { "" 1 } } ))))

(deftest generate-tuple-frequency-map-test
  (testing "Generates a tuple-based frequency map from the vector of words"
    (is (= (generate-tuple-frequency-map ["this" "is" "this" "is" "this" "is" "the" "way" "this" "will" "be"]) { ["this" "is"] { "this" 2 "the" 1 } ["is" "this"] { "is" 2 } ["is" "the"] { "way" 1 } ["the" "way"] { "this" 1 } ["way" "this"] { "will" 1 } ["this" "will"] { "be" 1 } } ))))

(deftest generate-nary-frequency-map-test
  (testing "Generates a tuple-based frequency map from the vector of words"
    (is (= (generate-nary-frequency-map ["this" "is" "this" "is" "this" "is" "the" "way" "this" "will" "be"] 2) { ["this" "is"] { "this" 2 "the" 1 } ["is" "this"] { "is" 2 } ["is" "the"] { "way" 1 } ["the" "way"] { "this" 1 } ["way" "this"] { "will" 1 } ["this" "will"] { "be" 1 } } ))))

(deftest generate-probability-map-test
  (testing "Generates a probability map from the frequency map"
    ))

; (deftest generate-tuple-frequency-map-test
;   (testing "Generates a frequency map of tuples to following words"
;     (is (= (generate-tuple-frequency-map ["this" "is" "this"
