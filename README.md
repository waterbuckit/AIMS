# AIMS - *Adam Is Mad Smart*

As the title suggests, this project *aims* to produce something, that being a somewhat usable piece of till software.

My intended features include:

* Basic till functionality.
* Loading of items from a datafile. 
* An end of day logging system.
* User management.
* Item/Inventory management.
* Receipt form management.

### Requirements
(Naturally, these will be maintained as I complete each component)
- [x] Load items
* (Strorage of items in memory, with access to them for addition to basket, etc)
- [ ] Manipulate items
* (Convenient way of adding new items into the system, without having to edit the data file)
- [x] On-screen entry mechanism
* (A fancy method of allowing numbers to be typed in via an accessible and touch-friendly numberpad)
- [x] Fast-select grid
* (Categorised view of items available, with a grid-like layout to make entry convenient)
- [x] Item list
* (Tracks items that have been added to the transaction)
- [x] User login system
* (Hashed password storage, denies unauthorised access through usage of multiple levels of permissions)
- [ ] Receipt generator
* (At the purchase screen, compiles all of the data of the purchase, including discounts and the amount of money that the cashier has been paid)
- [ ] Status bar 
* (Displays any necessary information in regards to the status of the system (till draw open/closed, user, till number, time))

#### Optional Extras
(Will add to this as I see fit)
- [ ] Server
* (Will allow external storage of inventory information, secure validation of transactions, offloading of processing, and another layer of security for authorisation)
- [ ] Hardware operation
* (Would allow to print out receipts on paper, read barcodes with a scanner)
- [ ] Fullscreen "kiosk" mode
* (Would add an aditional level of security, where a malicious user cannot escape the till interface)
