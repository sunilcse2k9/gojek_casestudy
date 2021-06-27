# Gojek Case Study


**Problem Statement:**
The goal of this assignment is to build a simple MasterDetail screen app which shows the current
 trending Github repositories fetched from a public API.


**Requirements:**
1. The app should preferably support minimum Android API level 21.
2. The app should fetch the trending repositories from the provided public API and display it to the
users.
3. While the data is being fetched, the app should show a loading state. Shimmer animation is
optional.
4. If the app is not able to fetch the data, then it should show an error state to the user with an
option to retry again.
5. All the items in the list should be in their collapsed state by default and can be expanded on
being tapped.
6. Tapping any item will expand it to show more details and collapse any other expanded item.
Tapping the same item in an expanded state should collapse it.
7. The app should have 100% offline support. Once the data is fetched successfully from remote, it
should be stored locally and served from cache until the cache expires.
8. The cached data should only be valid for a duration of 2 hour. After that the app should attempt
to refresh the data from remote and purge the cache only after successful data fetch.
9. The app should give a pull-to-refresh option to the user to force fetch data from remote.
