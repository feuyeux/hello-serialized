# Hello Serialized

## Candidates

- [JProtobuf](https://github.com/jhunters/jprotobuf) is a powerful Java protobuf library
- [Apache Fury](https://github.com/apache/fury) is a blazingly-fast multi-language serialization framework powered by JIT (just-in-time compilation) and zero-copy. demo: [hello-furyio](https://github.com/feuyeux/hello-furyio)
- [Fastjson](https://github.com/alibaba/fastjson) is a Java library that can be used to convert Java Objects into their JSON representation. 
- [Fastjson2](https://github.com/alibaba/fastjson2) is an upgrade of the FASTJSON, with the goal of providing a highly optimized JSON library for the next ten years.
- [Jackson](https://github.com/FasterXML/jackson) is a multi-purpose Java library for processing JSON data.
- [Gson](https://github.com/google/gson) is a Java library that can be used to convert Java Objects into their JSON representation.

## GrandBench Results (sorted by Score)
Benchmark                  Mode  Cnt        Score   Error  Units
GrandBench.testFury       thrpt        941612.623          ops/s
GrandBench.testJProtobuf  thrpt        154968.925          ops/s
GrandBench.testJackson    thrpt         70253.544          ops/s
GrandBench.testFast2Json  thrpt         59035.498          ops/s
GrandBench.testFastJson   thrpt         43323.395          ops/s
GrandBench.testGson       thrpt         20956.183          ops/s

## MoyenBench Results (sorted by Score)
Benchmark                  Mode  Cnt       Score   Error  Units
MoyenBench.testFury       thrpt       972184.230          ops/s
MoyenBench.testFast2Json  thrpt       288178.631          ops/s
MoyenBench.testFastJson   thrpt       179185.558          ops/s
MoyenBench.testJProtobuf  thrpt       135463.377          ops/s
MoyenBench.testJackson    thrpt        72116.166          ops/s
MoyenBench.testGson       thrpt        59473.264          ops/s

## PetitBench Results (sorted by Score)
Benchmark                  Mode  Cnt       Score   Error  Units
PetitBench.testFury       thrpt       9531667.839          ops/s
PetitBench.testFast2Json  thrpt       4892763.856          ops/s
PetitBench.testJProtobuf  thrpt       4051234.408          ops/s
PetitBench.testFastJson   thrpt       2924807.093          ops/s
PetitBench.testJackson    thrpt        948208.195          ops/s
PetitBench.testGson       thrpt        668335.770          ops/s