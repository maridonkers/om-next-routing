(ns yourproject.core
  (:require [yourproject.util :as util]
            [yourproject.routing :as routing]))

(enable-console-print!)

;; -------------------------
;; Set-up.
(routing/om-next-root!)
(routing/hook-browser-navigation!)
(routing/restore-page!)
