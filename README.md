# Emergency Tanzania

## Version 1 service scope

This version supports mainland Tanzania emergency contacts only.

| Service | Number |
| --- | --- |
| Police | 112 |
| Fire and Rescue | 114 |
| Ambulance | 115 |

Last verified: 2026-09-14

Sources:

- https://www.tanzania.go.tz/faqs
- https://www.polisi.go.tz/mawasiliano/

Direct one-tap calling is implemented in Stage 2.

## Safe testing

Debug builds never call the emergency numbers. To test calling on your own phone, add this line to the project's `local.properties` file:

```properties
DEBUG_TEST_PHONE_NUMBER=+255XXXXXXXXX
```

Replace the value with a phone number you own or are authorized to call, then sync and run the `debug` build. `local.properties` is excluded from version control, so this number will not be committed. Release builds use the verified emergency numbers above.
