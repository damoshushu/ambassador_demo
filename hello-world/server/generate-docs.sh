docker run --rm -v $(pwd)/doc:/out -v $(pwd)/src/main/proto:/protos pseudomuto/protoc-gen-doc --doc_opt=html,index.html
