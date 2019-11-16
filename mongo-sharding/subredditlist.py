from bs4 import BeautifulSoup as Soup
import requests
import json
import time


ua = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:70.0) Gecko/20100101 Firefox/70.0"
site = "http://redditlist.com/sfw?page="
pages = range(1, 11)
filename_output = "posts_14112019_new.json"

subreddit_set = set()
for page_index in pages:
    print("Scraping page", page_index)
    req_site = requests.get(site+str(page_index))
    soup_site = Soup(req_site.text, "html.parser")

    subreddit_list = soup_site.findAll("a", {"class": "sfw", "target": "_blank"})
    for subreddit in subreddit_list:
        subreddit_set.add(subreddit.string)

print("Found", len(subreddit_set), "subreddits")

with open(filename_output, "w") as post_file:
    subreddit_index = 0
    for subreddit in subreddit_set:
        post_count = 0
        subreddit_index += 1
        print(subreddit_index, "Processing subreddit", "/r/"+subreddit, ">> ", end="")
        try:
            sub_response = requests.get("https://www.reddit.com/r/"+subreddit+".json", headers = {'User-Agent': ua})
            if sub_response.status_code != 200:
                print("Looks like we are blocked trying to access", "/r/"+subreddit, "reason", sub_response.status_code)
                time.sleep(5)
                continue
            for post in sub_response.json()["data"]["children"]:
                data = post["data"]
                todump = {
                        "id": data["id"],
                        "author": data["author"], 
                        "title": data["title"], 
                        "subreddit": data["subreddit"], 
                        "upvotes": data["ups"], 
                        "downvotes": data["downs"], 
                        "score": data["score"],
                        "awards": data["total_awards_received"],
                        "oc": data["is_original_content"],
                        "created": data["created"],
                }
                post_file.writelines(json.dumps(todump)+"\n")
                post_count += 1
            print("Got", post_count, "posts from", "/r/"+subreddit)
            print("Sleeping for 4 seconds\r", end="")
            time.sleep(3)
        except:
            print("Error connecting to subreddit", subreddit, "Sleeping for 10 secs")
            time.sleep(10)
            continue

