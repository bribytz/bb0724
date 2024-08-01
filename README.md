# bb0724

### Setup
- Uses java 17
- Uses gradle wrapper ex `./gradlew clean build`

### Notes/Future Improvements/Questions
- Warning on number of days if greater than X days (so if they enter 100 instead of 10, they'd get a warning, but that'd probably happen on the UI)
- Date validation so that past checkouts can't happen
    - or if this did need to happen, maybe lock this to an admin/supervisor role
- Add more validations/null checks (especially around dates)
- Add submission endpoint 