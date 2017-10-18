(ns clojush.instructions.return
  (:use [clojush pushstate util]))

(defn returner
  "Returns a function that takes a state and moves the top literal
   from the appropriate stack to the return stack."
  [type]
  (fn [state]
    (if (empty? (type state))
      state
      (let [item (top-item type state)]
        (push-item {:type type :item item} :return (pop-item type state))))))

(define-registered return_fromexec (with-meta (returner :exec) {:stack-types [:environment :exec] :parentheses 1}))
(define-registered return_fromcode (with-meta (returner :code) {:stack-types [:environment :code]}))
(define-registered return_frominteger (with-meta (returner :integer) {:stack-types [:environment :integer]}))
(define-registered return_fromfloat (with-meta (returner :float) {:stack-types [:environment :float]}))
(define-registered return_fromboolean (with-meta (returner :boolean) {:stack-types [:environment :boolean]}))
(define-registered return_fromboolean (with-meta (returner :zip) {:stack-types [:environment :zip]}))
(define-registered return_fromstring (with-meta (returner :string) {:stack-types [:environment :string]}))
(define-registered return_fromchar (with-meta (returner :char) {:stack-types [:environment :char]}))
(define-registered return_fromgenome (with-meta (returner :genome) {:stack-types [:environment :genome]}))

(define-registered
  return_exec_pop
  ^{:stack-types [:environment :exec]}
  (fn [state]
    (assoc state :return (list-concat (list 'exec_pop) (:return state)))))

(define-registered
  return_code_pop
  ^{:stack-types [:environment :code]}
  (fn [state]
    (assoc state :return (list-concat (list 'code_pop) (:return state)))))

(define-registered
  return_integer_pop
  ^{:stack-types [:environment :integer]}
  (fn [state]
    (assoc state :return (list-concat (list 'integer_pop) (:return state)))))

(define-registered
  return_float_pop
  ^{:stack-types [:environment :float]}
  (fn [state]
    (assoc state :return (list-concat (list 'float_pop) (:return state)))))

(define-registered
  return_boolean_pop
  ^{:stack-types [:environment :boolean]}
  (fn [state]
    (assoc state :return (list-concat (list 'boolean_pop) (:return state)))))

(define-registered
  return_zip_pop
  ^{:stack-types [:environment :zip]}
  (fn [state]
    (assoc state :return (list-concat (list 'zip_pop) (:return state)))))

(define-registered
  return_string_pop
  ^{:stack-types [:environment :string]}
  (fn [state]
    (assoc state :return (list-concat (list 'string_pop) (:return state)))))

(define-registered
  return_char_pop
  ^{:stack-types [:environment :char]}
  (fn [state]
    (assoc state :return (list-concat (list 'char_pop) (:return state)))))

; Immediately copies the current tagspace to the environment on the top
; of the :environment stack.
(define-registered
  return_tagspace
  ^{:stack-types [:environment]}
  (fn [state]
    (if (empty? (:environment state))
      state
      (let [top-env (top-item :environment state)
            new-env (assoc top-env :tag (:tag state))]
        (push-item new-env :environment (pop-item :environment state))))))

