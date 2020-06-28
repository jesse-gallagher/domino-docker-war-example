# Domino Docker Webapp Example

This project shows an example of building a Jakarta EE/MicroProfile-based webapp that runs with a Domino server runtime.

It is specifically _not_ a running Domino server: though it uses the Notes runtime from the Domino Docker image, it launches
only an Open Liberty server using the server ID, not a Domino server.

The original structure of the project is based on the Open Liberty template available when creating projects using
[Codewind](https://www.eclipse.org/codewind/), but was significantly stripped down from there.

## Requirements

This project requires that the official Domino 11.0.1 Docker image from HCL be installed in your building Docker environment.

## Running

To date, I have run this specifically in Codewind in Eclipse. It should run in other IDEs
and in Docker normally, but to date I've only used Codewind.

## Customizing the Notes ID and config

The "notesdata" directory contains a one-off server ID file and stripped-down notes.ini and names.nsf files to go with it. To
customize for your domain, copy in a server ID of your own, an accompanying names.nsf, and tweak the notes.ini file to match. 