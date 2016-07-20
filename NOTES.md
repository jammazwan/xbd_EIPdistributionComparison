### Same Model and Use Case, 4 EIP Patterns

 * **RecipientList**
 * **Multicast**
 * **RoutingSlip
 * **DynamicRouter**

Use case: sending **invites to your social group for a camping weekend**, and sometimes tracking responses (depending on the EIP pattern).

It is intended to clarify the behavior and differences of the 4 patterns relative to each other, as well as cover some of the easier Camel syntax/semantics.


### Caution: More Complex Than Most X_Projects

This project comes close to breaking the intent of jammazwan example projects, which is to show an isolated camel feature in a 60 second TTFHW.

It will do the TTFHW part fine, but it's hardly an isolated feature. Instead it shows what should hopefully be the exact minimum required to get a realistic comparison between the 4 most common similar EIP patterns. 

Novices are warned, accordingly.

### EIPdistributionComparison NOTES:


Here's a summary I made from the two sources I have read on these 4 CBR EIPs. Hopefully it is close to correct. 

Please note that I was not always able to perfectly reconcile apparent differences between the Hophe/Woolf EIP book, and the Isben/Anstey Camel in Action book. So I probably steered closer to the Camel In Action Book. Notable examples of differences include a deliberately articulated Control Channel for the Dynamic Router in the EIP book, but there may be others.

 * **RecipientList** - sends, doesn't receive reply. List determined by content of message.
 * **Multicast** - Like above, but sends as a request, receives reply. Typically consolidates replies with Aggregator.
 * **RoutingSlip** - Like above, but each message sent in a consecutive sequence. Predetermined RoutingSlip determines sequence of recipients.
 * **DynamicRouter** - Like above, except re-calculates who receives what, after each request/reply.

The basic idea is to come up with a task that is executed differently with each of the EIPs, to compare capabilities and usages of each. 

### Artifact Configuration

There are 4 standalone tests, one for each of the EIPs. Each test has

 * a companion camel-....xml
 * a companion ...Processor
 * a companion output_[eip].txt file to write out the results of the exchanges

### Task Design

**The Story** goes like this: 

There is a campout coming up. We want to invite the active members of the group, and with this invite, include whatever they food they are assigned to bring. 

 * There are 27 members of the group, a random generator is used to simulate which are active and which are not active.
 * The campsite hosts 15 people, so the active members are the only ones invited. 
 * 15 of the 27 members are active members.
 * Ideally, when active members decline the RSVP, an inactive member is invited to replace him, and so on, until the list is exhausted or 15 Yes answers are received. But this must be an automated process, because the organizer does not have time to do this.
 * Approx 20% of active members decline. Approx 50% of inactive members decline.
 * The list of food is a hard coded list of 15 items.
 * The output_[eip].txt file is used by the organizer to collect data and make final adjustments the day before the campout.
 * RSVPs are received instantaneously (not very realistic, but heh)
 * Different EIPs will handle the RSVPs differently, or not at all
 * There is an un-named organizer who cleans up, and takes care of whatever the EIP does not do, magically. This is the primary function of the output file.
 * see Hackish: below - enough code was written to prove the story as reasonable with these patterns. But only that much was written.

### RecipientList

Per above this EIP is a send only, so RSVPs are not collected as part of the system. 

The output_recipientList.txt only includes who was invited, and what they were assigned. 

As approx 20% will decline, only 12 of the 15 will show up for the campout.

Since the organizer knows nothing until he sees who shows up with what, he/she has to make a food run after everyone shows, for whatever is missing.

### Multicast

Handles the RSVP by writing each RSVP to output_multicast.txt.

As approx 20% will decline, only 12 of the 15 will show up for the campout.

The output file is still a single batch, but at least it includes who declined. So the organizer will be able to make the food run for the 3 missing food items on the way to the campout, rather than after it gets started.

_Programming note: Multicast, unlike the other three EIPs, does not have it's own bean @Multicast annotation. So a different method had to be used to provide the list of targets. Otherwise, it is identical code to RecipientList in most respects._

### RoutingSlip

A consecutive send of invites offers this task the benefit of modifying the content of the list for each RSVP decline.

In other words, if the previous recipient was assigned pickles but declined the invite, pickles could be added to the next person's list.

As approx 20% will decline, only 12 of the 15 will show up for the campout. Depending on whether the last person invited was one of the declines or not, the output file will not have any food items still remaining to be brought by the organizer.


### DynamicRouter

The Dynamic Router variant is the only variant capable of adding back inactive members and inviting them, with food item assignment. 

Depending on the luck of the random number generator, 15 people will show up at the campout and the organizer won't have to bring any food.

### Hackish:

 * processors used instead of beans just to have full access to all of exchange, easily. Not as elegant as Claus Isben would suggest.
 * used statics for populating test data which would probably be inappropriate for a real project
 * used "direct:MemberName" for routing targets, which would be questionable in a real project. More typically, routing targets might be components rather than individuals
 * no attempt was made to produce anything like realistic messages to members of what they were to bring. Program was written to act as if this had taken place, and record the appropriate next step. This piece was not deemed to be relevant to the demo.
 * food reassignment code in RoutingSlip works most of the time, sort of. Well enough to prove that it could work. But it null pointers sometimes, probably when the last or next to the last person declines. No attempt was made to fix this as it is just proof of concept, not production code. Just re-run it if you get a null pointer.
 * There are more bugs. But enough is written to demonstrate the key capabilities of the four approaches



### Tests

Testing is generally lame, enough to detect whether the output file was written.
