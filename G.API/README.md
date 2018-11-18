# G.API

## Group Algol - Joseph Petitti, Justin Cheng, Andrew Levy, Matthew Hagan

Notes:

We made changes from our G.1 Analysis based on feedback and implemented the new
version in the design of this API.

For the ShowWeekSchedule use case we didn't want the client to have to download
the entire schedule object. This is because the schedule could be quite large
(maybe it's ten thousand years long or something), and because the schedule
object contains the secret code, which we don't want participants to have access
to. Instead we created a WeeklySchedule object, which contains just the time
slots and meetings for the requested week. The server will construct the
WeeklySchedule object on the fly when the showweeklyschedule API call is made.
