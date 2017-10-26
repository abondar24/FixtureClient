# Fixture client

Problem Description
===================
A small web server is provied.  It is written in
Python 2.7. 

By default the web server listens on port 7299.

The web server has three endpoints:
  /source/a
  /source/b
  /sink/a

Source A emits JSON records.
Source B emits XML records.
Sink A accepts JSON records.

Most records from Source A will have a corresponding record
from Source B.  These are "joined" records.

Some records from one source will not have a match from the
other source. These are "orphaned" records.

Some records are malformed.  These are "defective" records.

The client  must read all the records from /source/a and
/source/b, categorize them as "joined", "orphaned", or "defective".
It must report the "joined' and "orphaned" records to /sink/a.  It
can ignore defective records.

The server emits  around 1000 records. Once
all the records have been read from an endpoint it responds with
a "done" message.

Here's the catch.  The source and sink endpoints are interlinked.
Sometimes they will block until data has been read from or written
to the other sinks.  When this happens the request will return a
406 response.  They will never deadlock.


Testing
=======
The web server writes your responses and the expected response
into its running directory. Check.sh script is used for comparision of the results


Message Specifications
======================

Endpoint /source/a
------------------
normal record: { "status": "ok", "id": "XXXXX" }
done record: {"status": "done"}

Endpoint /source/b
------------------
normal record:
<?xml version="1.0" encoding="UTF-8"?><msg><id value="$ID"/></msg>

done record:
<?xml version="1.0" encoding="UTF-8"?><msg><done/></msg>

Endpoint /sink/a
----------------
To endpoint in POST body:
{"kind": "$KIND", "id": "$ID"},
where $KIND can be either "joined" or "orphaned", and $ID is the $ID from the originating messages.

Success response:
{"status": "ok"}

Failure response:
{"status": "fail"}


How To Use The Tools
====================
* Execute the web server by running:
    python fixture.py
* The output will appear in the files out.txt and res.txt
  in the fixture's execution directory.
* Compare the out.txt and res.txt files by executing:
    bash check.sh
* For the comparison program, no output is good output


# Install client and run
====================

To compile the client code run 
```yaml
mvn clean install
```

To run the compiled program
```yaml
chmod a+x run.sh
./run.sh
```
