# tomcat-re-frame-datatables

An example of how to develop a re-frame Single Page Application with Ajax and Server-Sent Events (SSE).

The server is a Tomcat WAR to be used with a Tomcat webserver, tested with Apache Tomcat 7. The server presents Ajax and SSE endpoints by leveraging Jersey. Note that the Ajax and SSE endpoints can be served up by any type of webserver and that Tomcat is used purely as an example.

The client is a leverages re-frame to provide the structure of the app. Ajax calls are handled by cljs-ajax and the SSE stream is handled by kvlt.

A custom DataTables component was written in order to display the JSON feed presented by SSE. As this component is being constantly updated, a specified unique key within the JSON object is used in order to preserve selections (in the case of the example a persons name). This custom component supports single-select and multi-select capabilities. Although it uses the styling of the Select DataTables Extension, the Select API and Javascript functions is not required due to the differences in expected behaviour.
