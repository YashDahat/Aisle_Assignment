# Aisle_Assignment

##Project 1:

Below is the implementation I came up with for the robust like update scheduler.

Assumption: Here we are assuming that our server is running on UTC time.

1. **Create a Separate Table for Time Zones:**
   - We will set up a new table in our database to store all unique time zones in which our users are located. This table should contain the time zone names.

2. **User Registration/Settings:**
   - When a new user registers or sets up their account, we will prompt them to provide their location or time zone. We will store the user's time zone name in the "users" table. If the time zone doesn’t exist in the time zone table, we will add it.

3. **Scheduled Job Execution:**
   - We will implement a scheduler that runs continuously and periodically checks the current UTC time for all the time zones listed in the time zone table. We will check if it matches UTC +/- delta with any time zone given in the time zone table.
   - We will run an API to get the time in UTC from the given time zone name from the time zone table.

4. **Computing the Delta:**
   - We will calculate the time difference (delta) between the current UTC time and the target refresh time, which is 12:00 pm UTC. (delta = UTC - 12:00)

5. **Database Query for Time Zones:**	
   - If any time zone name matches the condition given above, we will query for those users and update the number of likes.

6. **Mobile App Time Zone Adjustment:**
   - In our mobile app, we will check the user's time zone when the app starts. If the time zone has changed due to the user travelling, we will update the time zone name in the "users" table.

7. **New Time Zones:**
    - If a user's time zone is not found in the "time_zones" table, we will add it to the table.

8. **Error Handling:**
    - We will implement proper error handling in case the scheduled job, database queries, or API calls fail. We will log errors and exceptions for debugging and troubleshooting.

By computing the delta as UTC - 12:00 pm and checking for matches in the "time_zones" table, we can efficiently handle the daily Likes refresh for users in their respective time zones. This approach ensures that users experience the Likes refresh at the same local time every day, regardless of their time zone. We will regularly test and monitor the system to ensure its reliability and accuracy.


##Project 2:

1. Implemented the Frontend and Backend Logic of entering the Phone no. screen and entering otp screen.
2. APIs were not working as desired, couldn’t get access tokens from /users/verify_otp API, so couldn’t get any results from /users/test_profile_list. Attaching screen shot from PostMan.
![image](https://github.com/YashDahat/Aisle_Assignment/assets/52702216/5d68cd30-8d58-4870-9655-9e32294f55c3)
![image](https://github.com/YashDahat/Aisle_Assignment/assets/52702216/bcfc17a0-ffd4-44f3-b940-37608b95d8d7)

3. Please have a look at your APIs as it seems they are not working.

