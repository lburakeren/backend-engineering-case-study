# Backend Engineering Case Study

## Description
This API manages user progress and daily World Cup tournament progress for a mobile game. It is built with Spring Boot, and user and tournament data are stored in a MySQL database. 
Additionally, the API follows the MVC structure, with specific tasks organized into different services.

## Core Classes
User: Manages details like the user’s level, username, country, coins, and rewards.

Tournament: Tracks tournament information, such as whether it is active and country scores.

TournamentGroup: Contains five participants from five different countries and manages participant details, the related tournament, and whether the group is ready to compete.

GroupParticipant: When a user joins a tournament and is assigned to a group, they become a group participant. This class holds the user’s information and their tournament score.

## Service Layer
TournamentOrganizer: Handles tournament entry requests. Uses all the services below and is also responsible for starting, stopping, and distributing rewards for the daily tournament.

UserService: Manages user creation, level updates, and reward claims.

TournamentService: Manages the tournament lifecycle, country scores, and leaderboards.

TournamentGroupService: Finds available groups for users based on their country and group capacity, manages those groups, and updates the group leaderboard.

GroupParticipantService: Manages group participants and updates their scores.



## Controller
TournamentEntryController: Handles enterTournament requests.

UserController: Handles createUser ad updateLevel requests.

TournamentController: Responsible for getCountryLeaderboard request.

TournamentGroupController: Manages group leaderboard retreval. (getGroupLeaderboardRequest)

GroupParticipantController: Provides group ranking information. (getGroupRankRequest)


## Important Design&Implementation Issues
### Finding an Available Group
When a user wants to join the tournament, the TournamentGroupService locates an available group. It does this by checking existing groups that are not fully filled and 
that do not already have a user from the same country. If no such group is available, a new group is created, and the user is added there. The TournamentGroupService 
keeps a list of groups that are not yet full and uses this list to efficiently match users with groups, ensuring each group meets the tournament’s country-specific requirements.

### Reward Distribution and Claim
Every day at 8:00 pm, the tournament ends automatically using a ScheduleJob. Afterward, the TournamentOrganizer retrieves all completed groups with the help of TournamentGroupService. 
It then checks the scores of participants in each group, and rewards are distributed to the top two players (highest and second-highest scores) via UserService. 
The reward coins are stored in the reward_coin column of the users table. Once the tournament has ended, users can claim their rewards through the claimReward request, 
which adds the reward to their total coins. Additionally, the reward_coin column is used to check if a user has already claimed their reward before entering a new tournament.

### User Level Update and Tournament Score Calculation
When a user’s level increases, UserService performs the necessary updates (adding 25 coins) if the user is not actively competing in a tournament group. 
However, if the user is currently competing in a tournament group, their tournament score is also incremented through GroupParticipantService, 
and their country’s score is updated via TournamentService. The isReady attribute in TournamentGroup is used to check if the group is actively competing at that moment.

