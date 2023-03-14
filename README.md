# ONE Coding Exercise: `onecache`

Imagine you've joined ONE (welcome!) and are working on your first big project.
You've been tasked with implementing a new feature in our codebase.
In the process, you run into some bugs coming from code that you didn't write.
What do you do?

In this interview, we're interested in seeing your ability to both
**debug** and **modify** existing code.
Specifically, we'd like you to **fix some bugs** in this small but
real-world codebase. Depending on the interview, we may only ask you to do
one of these.

The docs below explain the code at a high level, and comments in the code explain the details.

## Background

This codebase implements a small **caching** library. Specifically, it provides:

- A generic **`Cache` interface** that abstracts away different cache backends,
  e.g. Redis vs. in-memory. It exposes methods like `get`, `set`, and `clear`.

- A **`RedisCache` implementation** of the `Cache` interface.
  This code is not included here, but a stub is shown for illustration.

- A **`MemoryCache` implementation** of the `Cache` interface using simple
  in-memory data structures (like dictionaries and linked lists).
  Supports capping memory usage with a `maxItems` parameter,
  and evicts the least recently read items when over capacity.

- A **`Scheduler` interface** that manages scheduled background tasks.

`MemoryCache` has a few bugs and doesn't yet implement support for expiring items.

## Process

We'll let you know the next bug or feature after you fix or implement each one.

We'll decide on the fly which bugs & features to focus on,
e.g. based on your background, experience, and what we see.
As a result, we may not actually touch all the code in this repo.

Even though this is an interview, we want you to be as comfortable and "at home"
as you normally would be in the real world.
So e.g. feel free to **add print logs, Google, Stack Overflow**, etc.
to help you debug & implement.
And **you don't even need to "narrate your thoughts"** to us if you don't want to.
Consider us silent observers, like a usability study.

Finally, we're interested in seeing your ability to figure things out on your own,
but feel free to use us as a [rubber duck](https://en.wikipedia.org/wiki/Rubber_duck_debugging)
if you get stuck.
We may refrain from answering questions that could be answered through the code,
but we'll let you know if that's the case.

Let us know if you have any questions. Otherwise, continue on to the instructions below
for how to get started. Let's do it! 🚀

## Instructions

1.  **Share your screen** if you're not already doing so. Thanks!

2.  **Run _one_ of the following** in your terminal, depending on your language:

    - Go:

      ```
      go test -run MemoryCache_Basic
      ```

    - JavaScript:

      ```
      npm install
      npm test -- --grep 'MemoryCache Basic'
      ```

    - Python:

      ```
      pip3 install -r requirements.txt
      python3 -m unittest memoryCache_test.TestMemoryCache.test_basic
      ```

    - Java:

      ```
      ./gradlew test --info --tests MemoryCacheTests.basic
      ```

    You should see a failing test!
    This is the first bug we'd like you to figure out & fix. 🐞

3.  Let us know if you have any questions
    about the format or logistics of this interview.

4.  When you're ready, go ahead fire up your editor and **start debugging**! 👩🏽‍💻
