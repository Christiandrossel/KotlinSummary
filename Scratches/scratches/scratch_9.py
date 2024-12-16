

def iterate_list_and_return_ids_from_key_value_store():
  products = {
    "user": [{"id": 1, "name": "Product 1"}, {"id": 2, "name": "Product 2"}],
    "customer": [{"id": 3, "name": "Product 3"}, {"id": 4, "name": "Product 4"}],
    "seller": [{"id": 5, "name": "Product 5"}, {"id": 6, "name": "Product 6"}]
  }

  ids = [product["id"] for key in products for product in products[key]]

  print(ids)


# main
if __name__ == "__main__":
  iterate_list_and_return_ids_from_key_value_store