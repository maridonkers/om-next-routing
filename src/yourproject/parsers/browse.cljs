(ns yourproject.parsers.browse
  (:require [om.next :as om]
            [yourproject.reconciler :refer [mutate read]]))

;;-----------------------
;; Parser read functions.
(defmethod read :page+browse
  [{:keys [state query]} k _]

  (let [st @state]
    {:value (om/db->tree query (get st k) st)}))

;;-------------------------
;; Parser mutate functions.
(defmethod mutate 'browse/increment
  [{:keys [state]} _ {:keys [count]}]
  {:action
   (fn []
     (swap! state
            update-in [:page+browse :count] inc))})

