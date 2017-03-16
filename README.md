# A proof of concept to use Finagle Finch with TLS

## How to reproduce

```bash
$ git clone git@github.com:nicojs/poc-finch-tls.git
$ cd poc-finch-tls
$ sbt run 
```
If you now inspect the TLS connection with `openssl s_client`, you'll get:

```
$ openssl s_client -showcerts -servername localhost -connect localhost:3808
CONNECTED(00000003)
140433661974160:error:140770FC:SSL routines:SSL23_GET_SERVER_HELLO:unknown protocol:s23_clnt.c:782:
---
no peer certificate available
---
No client certificate CA names sent
---
SSL handshake has read 7 bytes and written 307 bytes
---
New, (NONE), Cipher is (NONE)
Secure Renegotiation IS NOT supported
Compression: NONE
Expansion: NONE
SSL-Session:
    Protocol  : TLSv1.2
    Cipher    : 0000
    Session-ID: 
    Session-ID-ctx: 
    Master-Key: 
    Key-Arg   : None
    PSK identity: None
    PSK identity hint: None
    SRP username: None
    Start Time: 1489663087
    Timeout   : 300 (sec)
    Verify return code: 0 (ok)
---
```

This effectively means that there's no TLS server listening (just plain HTTP, which shouldn't even exist, but we'll ignore that for now).

I would expect something like:

```
$ openssl s_client -showcerts -servername localhost -connect localhost:38082
CONNECTED(00000003)
depth=0 C = NL, ST = NH, L = , O = , OU = , CN = , emailAddress = 
verify error:num=18:self signed certificate
verify return:1
depth=0 C = NL, ST = NH, L = , O = , OU = , CN = , emailAddress = 
verify return:1
---
Certificate chain
 0 s:/C=NL/ST=NH/L=/O=/OU=/CN=/emailAddress=
   i:/C=NL/ST=NH/L=/O=/OU=/CN=/emailAddress=
-----BEGIN CERTIFICATE-----
MIID4zCCAsugAwIBAgIJALERpDjAikNGMA0GCSqGSIb3DQEBCwUAMIGHMQswCQYD
VQQGEwJOTDELMAkGA1UECAwCTkgxEjAQBgNVBAcMCUFtc3RlcmRhbTEMMAoGA1UE
CgwDSU5HMQ8wDQYDVQQLDAZYLVdpbmcxFDASBgNVBAMMC1N2ZW4gS29zdGVyMSIw
IAYJKoZIhvcNAQkBFhNzdmVuLmtvc3RlcjJAaW5nLm5sMB4XDTE2MDQxNTA4MDAw
NloXDTE5MDIwMzA4MDAwNlowgYcxCzAJBgNVBAYTAk5MMQswCQYDVQQIDAJOSDES
MBAGA1UEBwwJQW1zdGVyZGFtMQwwCgYDVQQKDANJTkcxDzANBgNVBAsMBlgtV2lu
ZzEUMBIGA1UEAwwLU3ZlbiBLb3N0ZXIxIjAgBgkqhkiG9w0BCQEWE3N2ZW4ua29z
dGVyMkBpbmcubmwwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQC+z1Ab
uHxIuDMXOtkiSB9GiTiKoSzFnVE99szDfrUbnFfO2v2j16g0S3C3Uc8axDgdUXV+
X1Ujp3gL1PKIBdNvsv/0Ie6lCQE295DNZ5Lslg0Rwo1ttj9p2eHIcNoo+PQuXixG
NdWwB0DpWo82h4kNiHMgcJuLMB2qNy32zb5etyhauOVj25+T51Gl6EvBtMTgO3PX
G32c13NnlOgqYp4mzG+77bYPf2TQQwQCfwcApmyFi+5PXKya6U6sIygdLo3zZ6B/
hutm1tineiOFpDcBfHY/8McWVSAJ/hlCmiL7xf0j+K/ACqwx5mTGy8Rsmk7DmpKu
unEtcti8XzCwdKLZAgMBAAGjUDBOMB0GA1UdDgQWBBRtyj9p/QkVz7Veb57C5CcE
johgYjAfBgNVHSMEGDAWgBRtyj9p/QkVz7Veb57C5CcEjohgYjAMBgNVHRMEBTAD
AQH/MA0GCSqGSIb3DQEBCwUAA4IBAQAXT4+yfnHquAeud9x9jS+UwUsV+BJPf9Nj
1ScPCHwZlcENTkN9JwzD2TuUYty9Sku5dSZ2ihRymvDuVgQUcedSoUJZHp1PsfOx
FBc22a+w6LSG0rrk8lZk6/Fkm86okWtoFpEBb4KlBdYY7lcel7YS8Q6MN/FEVbj6
czls/P0Vh4XCvhsJdvKbigNnxXeJv+zxMU7QJdc5b20O78ClTXu/qffT9OSYwRyM
7zlrWbdEQWJ/6s4uUhBdzFh1r7UP2lUuNRXGYwrSVAffPuzxAKiPaYHDhN2aKwYr
s8dPxuqWT1ovenv0ky/JURp/nSWxeQkTBR2gCh/u8Pa7jDdCSJFV
-----END CERTIFICATE-----
---
```