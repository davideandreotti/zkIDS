# Content
- full_simulations folder includes tests where all the three components are active (Server, Middlebox, Client) and the policies are verified on real TLS exchanges captured on the localhost network
- Test_* folders include iterated tests: only the middlebox circuit execution is measured, iterated, where for each iteration a circuit parameter changes:
  - HTTP_String and HTTP_Merkle_Token: HTTP maximum payload size
  - HTTP_Merkle: merkle tree height
