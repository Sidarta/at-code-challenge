# at-code-challenge

## Initial Considerations

Test went well and proposed requisites were very nice to deal with. Hope you like it ;)

All blockers and all optional were delivered as specified on the challenge white paper. Please find below a few details on the project hereby delivered.

## Architecture

Project was refactored to use an implementation of MVP Architecture. This way, even though it is a simple project, it is way more organized and maintainable. This implementation allowed for the resolution of 2 of the blockers: remove logic from home activity and get rid of splash screen. Cache was maintained. 

## Layouts

Layout for the movies list is the same as base code. Layout for movie details is a simple proposed layout, with information required and a few other interesting information, to make the layout more appealing. Please keep in mind that layout is not more elaborate because of time constraints. It could, though, have a few interesting animations and interactions.

## Pagination

Pagination was implemented in the usual way and should allow for infinite scrolling. For testing purposed, localization of API (API Interceptor) was commented so evaluators can fully check pagination. This was done because localized queries and API results were very short and sometimes inconsistent, not making for a long list where we are able to keep scrolling.

## Network Layer

Implemented best practices on Retrofit Service class and included Interceptors for API Key and for Localization (language and region). Please keep in mind that localization was disabled (commented) for a longer list of results to test pagination. This implementation of Retrofit also made possible for removal of BaseActivity.

## Orientation Changes

Code was adjusted on the Manifest, so data is not reloaded when orientation changes. Callback for more sophisticated layout adjustments were declared (overridden) but just for clarity purposes. For this test no specific treatment was needed.

## Search

Search was implemented on the same activity, with a search bar. If more time was available, I would do a better implementation, on another activity (Searchable). Also, because of the current implementation, there could be a few not treated corner cases.
