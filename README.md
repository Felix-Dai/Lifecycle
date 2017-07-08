# Lifecycle

With Lifecycle, you can easily trace all the lifecycle events on all Activities with a single command.

```Java
Lifecycle.activity().onAll().with(new ACTIVITIES.OnStart() {
            @Override
            public void onStart(Activity activity) {
                // do onStart things
            }
        }).with(new ACTIVITIES.OnStop() {
            @Override
            public void onStop(Activity activity) {
                // do onStop things
            }
        }).listen();
```

And you can narrow your targets to a given class or instance, like
```Java
Lifecycle.activity(MainActivity.class).with(...).listen();
```
or
```Java
Lifecycle.activity((Activity)dialog.getContext()).with(...).listen();
```

For Fragments, Lifecycle works the same way, except it supports to select instances only on a given FragmentManger, like
 ```Java
Lifecycle.fragment().onAll().with(...).on(getSupportFragmentManager(), true).listen();
 ```
 
 Have fun:)