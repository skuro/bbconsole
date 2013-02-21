(ns bbconsole.core
  (:use bbconsole.commands))

(def intro
  (str
   "\"With great power comes great responsibility\" -- Anonymous\n"
   "Welcome to bbconsole v0.1-SNAPSHOT, type (help) to get a start"))

(def ^:dynamic *switch* (atom true))

(defn valid?
  "Makes sure you're not abusing the console, you naughty programmer!
Returns the reference to the fn implementing the command"
  [command]
  (and (list? command)
       (let [[fun & params] command]
         (and (nil? (namespace fun))
              (resolve (symbol "bbconsole.commands" (str fun)))))))

(defn unknown [command]
  (println "Invalid command:" command))

(defn execute-command
  "Executes commands, one at a time. Every command must be a sexp with the first
element evaluating to a function in bbconsole.commands"
  [command]
  (if-let [fun (valid? command)]
    (try
      (apply fun (rest command))
      (catch Exception e
        (unknown command)
        (.printStackTrace e)))
    (unknown command)))

(defn main-loop
  "Runs indefinitely waiting for your commands"
  []
  (while @*switch*
    (do
      (print "> ")
      (flush)
      (execute-command (read-string (read-line))))))

(defn interactive
  "Runs an interactive shell waiting for commands to be entered"
  []
  (println intro)
  (main-loop))

(defn -main [& args]
  (interactive))
