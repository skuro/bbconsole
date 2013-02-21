# bbconsole

A Clojure library to trigger actions on a Backbase portal. From an idea of [`@tonvanbart`](https://github.com/tonvanbart).

## Usage

Directly from the sources, run

    lein run

Alternatively, the same can be achieved by

    lein uberjar
    java -jar target/bbconsole-0.1.0-SNAPSHOT-standalone.jar

## Available commands

The following is the output of the `(help)` command:

```
Available commands:
	(help)
	prints this help text

	(select-host host-or-ip)
	selects the host to operate on [default: localhost]

	(quit)
	stops the program and exits. C-c will work also

	(select-portal name)
	selects a portal called name. the portal must exist

	(create-portal name)
	creates a new portal with the given name

	(inspect)
	dumps the current operation context
```

## License

Copyright © 2013 Carlo Sciolla

Distributed under the Eclipse Public License, the same as Clojure.
