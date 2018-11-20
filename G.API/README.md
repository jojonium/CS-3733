# G.API

## Group Algol - Joseph Petitti, Justin Cheng, Andrew Levy, Matthew Hagan

Notes:

We made changes from our G.1 Analysis based on feedback and implemented the new
version in the design of this API.

We decided to only have only the `TimeSlot` class, instead of `TimeSlot` and
`Meeting`. `TimeSlot` can have a secret code for participants and a string,
`requester`, that contains the name of the participant with a meeting at that
time. If `requester` is empty that means the time slot is unfilled.

This way, `ShowWeekSchedule` can send an array of `TimeSlot`s back to the client
containing only the time slots for that particular week. This makes it so that
the server doesn't have to send the entire `Schedule` object back to the client.
This is good because the object could be very large (maybe the schedule is
10,000 years long or something), and because the object contains the secret
code, which we don't want to reveal to participants.
