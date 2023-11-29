import json

with open('node.json', 'r') as originalFile:
    origianlData = json.load(originalFile)

nodeList = []

for node in origianlData:

    outputNode = {}

    outputNode["code"] = node["code"]
    outputNode["centralNode"] = node["centralNode"]
    outputNode["latitude"] = node["latitude"]
    outputNode["longitude"] = node["longitude"]

    nearNodes = []

    if node["nearNode0"] is not None:
        nearNodes.append({"code": node["nearNode0"], "weight": node["weight0"]})

    if node["nearNode1"] is not None:
        nearNodes.append({"code": node["nearNode1"], "weight": node["weight1"]})

    if node["nearNode2"] is not None:
        nearNodes.append({"code": node["nearNode2"], "weight": node["weight2"]})

    if node["nearNode3"] is not None:
        nearNodes.append({"code": node["nearNode3"], "weight": node["weight3"]})

    if node["nearNode4"] is not None:
        nearNodes.append({"code": node["nearNode4"], "weight": node["weight4"]})

    if node["nearNode5"] is not None:
        nearNodes.append({"code": node["nearNode5"], "weight": node["weight5"]})

    if node["nearNode6"] is not None:
        nearNodes.append({"code": node["nearNode6"], "weight": node["weight6"]})

    if node["nearNode7"] is not None:
        nearNodes.append({"code": node["nearNode7"], "weight": node["weight7"]})

    outputNode["nearNodes"] = nearNodes
    nodeList.append(outputNode)

    print(outputNode)

with open("outputNode.json", "w") as outputFile:
    json.dump(nodeList, outputFile, indent="\t")

