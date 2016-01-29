(ns yourproject.parsers.app
  (:require [om.next :as om]
            [yourproject.reconciler :refer [mutate read]]))

(defmethod read :app
  [{:keys [state query]} k _]

  (let [st @state]
    {:value (om/db->tree query (get st k) st)}))

(defmethod mutate 'app/login
  [{:keys [state]} _ {:keys [logged-in?]}]

  {:action (fn []
             (swap! state
                    assoc-in [:app :logged-in?] true))})

(defmethod mutate 'app/logout
  [{:keys [state]} _ {:keys [logged-in?]}]

  {:action (fn []
             (swap! state
                    assoc-in [:app :logged-in?] false))})

