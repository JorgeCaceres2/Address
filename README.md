## Challenge 1
### Simple API integration and data output through an endpoint.  
- Input: Part of a real address in the US. City, state and zipCode as optional values. 
- Output: Complete addresses, containing City, State and ZipCode. 
- SDK: https://github.com/lob/lob-java
- ENDPOINT: https://docs.lob.com/#operation/autocompletion
- Api-Key: `live_pub_a15953d6b86c7028cbf7605ed360eee`
- Postman: export the collection/request used to call the endpoint and put it into the `/postman` directory.

### Notes
- Test api-key: `test_0dc8d51e0acffcb1880e0f19c79b2f5b0cc`
- Provided api-key has some limitations in lob endpoints, I used test api-key for basic calls
- Unit Test classes have been added 
