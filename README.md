
# Custom Caching Layer

## Overview

This project implements a custom caching layer with an LRU (Least Recently Used) eviction policy, thread safety, and persistence. The cache supports basic operations (`set`, `get`, `delete`), provides statistics, and allows dynamic configuration.

## Setup and Usage

### Prerequisites

- Java 8 or higher
- Gradle (for building the project)

### Setup

1. Clone the repository:
   ```sh
   git clone <repository-url>

   
### Running the Application use following command
javac LRUCache.java

java LRUCache


### Design Decisions
Data Structures:

A HashMap for O(1) access to cache entries.
A doubly linked list to maintain the LRU order.
Thread Safety:

Used ReentrantLock to ensure thread-safe operations.
Persistence:

Implemented using Java's Serializable interface for easy save and load operations.
Dynamic Configuration:

Cache capacity can be adjusted at runtime.
	
