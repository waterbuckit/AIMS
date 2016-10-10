# AIMS

As the title suggests, this project "aims" to produce something, that being a somewhat usable piece of till software.

My intended features include:

* Basic till operation.
* A way for items to be loaded into objects, and thus manipulated through the till.
* An inventory management system, that can be mapped to all the objects within the shop.
* An end of day logging system.
* Ability to add users, items, and change receipt form.

### Components
(Naturally, these will be maintained as I complete each component)
- [x] Item loader 
* (Conversion of text into item objects, and placement into an arrayList)
- [ ] Item manipulation 
* (This will allow addition of new items with all necessary member variables being addressed)
- [x] Numberpad appending mechanism
* (A fancy method of allowing numbers to be typed in via a swing numberpad)
- [ ] Item grid populator
* (Places all the items that are stored in the array list onto the grid of items that have the boolean "unscannable", they will also be categorised)
- [ ] Item list
* (Allows creation of a purchase, and allows the user to see what has been purchased in the form of a list (perhaps table, undecided) with the quantity and price displayed.)
- [ ] User login system
* (Hashed password storage, allows the program to deliver the necessary privileges to the user)
- [ ] Receipt generator
* (Works as part of the purchase page, compiles all of the data of the purchase, including discounts and the amount of money that the cashier has been paid.)
- [ ] Status bar 
* (Displays any necessary information in regards to the status of the system (till draw open/closed, user, till number, time))

#### Optional components
(Will add to this as I see fit)
- [ ] Server
* (Will allow external storage of user data)
