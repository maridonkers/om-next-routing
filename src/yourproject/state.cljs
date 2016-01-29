(ns yourproject.state)

;; Initial contents of (in-memory) state. Manually normalized, hence atom.
(def initial
  (atom {:app {:lc :nl-NL
               :logged-in? false ;;TODO derive from server state
               :dimensions {:orientation :landscape
                            :width 1024
                            :height 768}}

         :navbar {:collapsed? true}
         
         :page+home {:title "HOME (to be done)"
                :count 0}

         :page+browse {:title "BROWSE (to be done)"
                  :count 0}

         :page+about {:title "ABOUT (to be done)"
                 :count 0}}))
