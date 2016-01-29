(ns yourproject.reconciler
  (:require [om.next :as om]
            [yourproject.state :as state]))

;; -------------------------
;; The Om-next read functions
(defmulti read
  "Read data from DataScript store."
  om/dispatch)

;; -----------------------------
;; The Om-next mutate functions.
(defmulti mutate
  "Mutate data in DataScript store."
  om/dispatch)


;; -------------------
;; The Om-next parser.
;;
(def parser
  "Om Next parser for read and mutate."
  (om/parser {:read read :mutate mutate}))

;; -------------------------
;; Configures Om-next read and mutate functions.
;;
(def reconciler
  (om/reconciler {:state state/initial
                  :parser parser}))
