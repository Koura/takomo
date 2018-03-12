## Development Mode

## Configuration

This project requires github api key and organization list to poll to be setup in configuration before the application can be run.
This can be done by creating a `config.end` file in the project root and inserting following kind of structure inside it:
```
{:api-key "key as string"
 :organisations ["vector" "of" "organisations"]}
```

### Run application:

```
lein clean
lein figwheel dev
```

Figwheel will automatically push cljs changes to the browser.

Wait a bit, then browse to [http://localhost:3449](http://localhost:3449).

## Production Build


To compile clojurescript to javascript:

```
lein clean
lein cljsbuild once min
```
