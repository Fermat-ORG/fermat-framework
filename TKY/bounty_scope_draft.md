# BOUNTY PROGRAM AGREEMENT

## PROJECT NAME
Artist and Tokenly platforms 

## VERSION
1.0

## ABSTRACT
The development of Artist and Tokenly platform looks to offer to a Fermat user an evironment to connect with an artist and listening bought music inside the Fermat system.

In Tokenly platform (TKY) we are allowing the following:

* Management of Identities.
* Download music bought through the music tokenly platform.
* Music file management (delete and re-download)
* Synchronize Fermat Tokenly identity with Tokenly music user.
* Visualization of followed artist.

In Artist platform (ART) we are allowing the following:

* Management of Identities.
* Link ART identities with External Platforms.
* Communities Usage for Actor Connections.
* Playing the music only provide by the TKY platform.

## SCOPE
If the project include Use Case Analysis you must offer at least the following deliverables: Platforms, Actors, functionality groups and use cases in detail. 

If the project includes Architecture Analysis you must follow Fermat’s technical guidelines and offer at least the following deliverables: Identification of platforms, layers, components and workflows.

If the project includes GUI/UX Design you must offer at least the following deliverables: Mockups and graphic design of every single screen of the app. Also the language package for the texts of the app in English.

If the project includes Implementation provide a list of features to develop sorted by priority. Each feature must have a short description, a size (% of the total) and the Fermat components involved. Example:

#### 1- TKY Management of Identities:  
Development of the screens and logic structures that are necessary to allow the creation and local updates that will be used as a connection with another identities. Also this identity must be used to authenticate with Tokenly system.  
**Size:** 10%   
**Fermat components:** Artist Identity Sub app, Fan Identity Sub app, Artist Identity plugin, Fan Identity plugin, Tokenly Extenal API plugin.

#### 2- TKY Download music bought through the music tokenly platform:  
The Music Tokenly platform is an external platform to buy music using the Tokenly Tokens, this is managed by Tokenly. TKY platform will use the music Tokenly protected API to getting access to Tokenly user account, look the songs bought by this user and download this songs to the user device. This only can be used by a fan with a Fan identity properly created.  
All the songs are encrypted by the Fermat Plugin File System and only this plugin can restore the information stored in the file.  
**Size:** 20%  
**Fermat components:** Fan Wallet Reference Wallet, Tokenly Song Wallet plugin, Fan Identity plugin, Tokenly External API plugin.

#### 3- TKY Music file managment:  
The Fan Wallet screen let the user a simple song management, like delete a song and download a deleted song.  
**Size:** 10%  
**Fermat components:** Fan Wallet Reference Wallet, Tokenly Song Wallet plugin, Fan Identity plugin, Tokenly External API plugin.

#### 4- TKY Synchronize Fermat Tokenly identity with Tokenly music user:  
The Fan Wallet screen labeled as songs will download automatically the songs the first time that the user with a Fan identity properly created, after this, the Fan Wallet will synchronize with tokenly after a predeterminate time. The user can be force a synchronize using a swipe down.  
**Size:** 10%  
**Fermat components:** Fan Wallet Reference Wallet, Tokenly Song Wallet plugin, Fan Identity plugin, Tokenly External API plugin.

#### 5- TKY Visualization of followed artist:  
The Fan Wallet screen labelled as following will show the swapbot information from the connected artists to the Fan identity, this information includes a swapbot artist icon, the swapbot artist name and the swapbot URL. The following fragment will use the swapbot URL to the the fan open in the device browser the artist swapbot site.  
**Size:** 10%  
**Fermat components:** Fan Wallet Reference Wallet, Tokenly Song Wallet plugin,Fan Identity plugin, Tokenly External API plugin.

#### 6- ART Management of Identities:  
Development of the screens and logic structures that are necessary to allow the creation and local updates that will be used as a connection with another identities. Also this identity can be linked with other Fermat platforms, in this version only is allowed link ART identities with TKY identities.   
**Size:** 10%  
**Fermat components:** Artist Identity Sub app, Fan Identity Sub app, Artist Identity plugin, Fan Identity plugin.

#### 7- ART Link ART identities with External Platforms:  
The identity screen will show a combo box with the list of identities compatibles to link with ART platform identities, in this version only is allowed link ART identities with TKY identities.  
**Size:** 10%  
**Fermat components:** Artist Identity Sub app, Fan Identity Sub app, Artist Identity plugin, Fan Identity plugin.

#### 8- ART Communities Usage for Actor Connections:  
Screen development and the necessary logic structures to allow list the identities which which we have a stable connection, sending a request to connect with another identity, list of community identities, list notifications of connection requests. Also block an identity and delete such connection.  
**Size:** 10%  
**Fermat components:** Artist Community Sub app, Fan community Sub app, Artist Actor Connection plugin, Fan Community plugin, Artist Actor Connection, Fan Actor Connection, Artist Actor Network Service, Fan Actor Connection.

#### 9- Playing the music only provide by the TKY platform:  
The ART music player will play the songs that the Tokenly Song Wallet can provide, which are stored encrypted in the device.  
**Size:** 10%  
**Fermat components:** Music Player Sub app, TKY Tokenly Song Wallet.


Also, explain the work that is excluded of this project version.

## EVALUATION
Provide the acceptance criteria for every deliverable defined in the scope.

## TERMS AND CONDITIONS: 
Only fill the underlined spaces that apply.

1- The team agrees that the implementation has two stages: functionality and beta testing.

2- The team understands and accepts that the functionality will be considered done when all the features described in the scope of this agreement are completed and tested in an alpha stage.

3- The team understand and accepts that the implementation of functionality must follow the Fermat’s technical guidelines. 

4- Component architecture and workflows are created in the Analytics System and the Interfaces in the API library of the platform involved.

5- The team understands and accepts that implementation will be evaluated by the @bounty-program-team and will include functionality test and review of Fermat’s technical guidelines compliance. 

6- The team understands and accepts that there is only one free review for functionality and one for Fermat’s technical guidelines compliance. The following reviews will cost the team 25% of the related bounty each in case the first one wasn’t approved.

7- The team agrees to complete the implementation on the following conditions: 

- **Implementation due date:** All the features will be finished before **16/05/2016**. 
 
- **Implementation collateral deposit:** The team agrees to deposit the amount of **2500** tokens, as a collateral to be lost if this part project is not approved before the due date.
 
- **Implementation margin:** No penalties are applied **7** calendar days after implementation due date.
 
- **Implementation penalty:** **5%** of the implementation bounty for each calendar day that elapses after the due date without formal acceptance from the @bounty-program-team.    
 
- **Implementation bounty:** The functionality will be **70%** of the total bounty. This bounty will be awarded to the development team when the @bounty-program-team considers that the functionality delivered is done.

14- The team understands and accepts that beta testing will be conducted by the @beta-testing-team.

15- The team understands and accepts that criteria to pass beta testing are: no bug issues on beta testing due date or in a period of three (3) consecutive calendar days before the due date.

16- The team agrees to complete the beta testing on the following conditions:

- **Beta testing due date:** Beta testing will be passed before **29/05/2016**. After this date, the beta testing bounty will be awarded to the @beta-testing-team. 
 
- **Beta testing collateral deposit:** The team agrees to deposit the amount of **0** tokens, as a collateral to be lost if this part of the project is not approved before the due date.
 
- **Beta testing margin:** No penalties are applied **5** calendar days after the beta testing due date.
 
- **Beta testing penalty:** **5%** of the bounty for each calendar day that elapses after the due date without formal passing through beta testing. The way to pass in this case is to have no bugs for (3) consecutive days. This penalty will be paid by the development team from the implementation bounty to the beta testing team.
 
- **Beta testing bounty:** The beta testing bounty will be a fixed 30% of the total bounty. It could be awarded to the development team if it passes the beta testing on time or by @beta-testing-team if they fails. It implies that development team will not get this bounty unless it succeeds in the beta testing process.

## TOTAL BOUNTY
The total amount of the bounty in Fermat tokens for this project **$10.000 (8000$ for implementation + 2400$ for beta testing).**

## DISTRIBUTION OF BOUNTY BY CONTRIBUTOR
The distribution of the bounty (once awarded and the functionality released). It must be defined beforehand and it will define not only how the bounty is distributed but also how deposits are contributed.
