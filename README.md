# ACDC

A clojure DDNS client? A Cloudflare DDNS client?

# Description

ACDC was made because I wanted a dynamic DNS client that worked with the latest Cloudflare API and was written Clojure.

Right now only Cloudflare is supported. I would not mind supporting more DDNS providers, but until I use them I won't be adding support myself. Of course if someone wanted it I would be willing to accept a PR.

ACDC is functional at the moment, but I don't consider it production ready until I can get some logging and better error handling. ACDC assumes a lot of connection attempts will succeed and has no means of correcting and not failing.

# Build

```shell
clj -T:build uber
```

# Usage

```shell
# run the clojure files
clj -M -m ddns.main

# or run the jar
./acdc-<x.x.x>-standalone.jar
```
