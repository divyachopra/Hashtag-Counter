# Hashtag-Counter
I have implemented a system to find the n most popular hashtags appeared on social media such as Facebook or Twitter. For the scope of this project hashtags will be given from an input file.This project was undertaken to improve my knowledge around advance data structures.
Basic idea for the implementation is to use a max priority structure to find out the most popular hashtags.
following structures are used for the implementation.
1. Max Fibonacci heap: use to keep track of the frequencies of hashtags.
2. Hash table: Key for the hash table is hashtag and value is pointer to the corresponding node in the Fibonacci heap.
