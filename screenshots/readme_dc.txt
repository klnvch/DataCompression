File->Open               - opens PNG or JPG files
"Compress" button        - starts compression process

Input

"Iteration limit"        - number of image blocks that teach the kohonen map
"Block size"             - size of blocks in which image is divaded
"Kohonen size"           - size of the kohonen map
"Learning rate: L0"      - learning rate for the kohonen map L0*EXP(-current_iteration/lambda)
"Low pass filter radius" - elemenates high-frequency coefficient after DCT

Output

top-left corner          - original image
top-right corner         - compressed image
"Bytes before"           - size of the image before compression assuming that RGB pixel is 3 bytes
"Bytes after"            - size of kohonen map blocks after compression
"Bytes for codebook"     - size of codebook that mathes blocks of image to kohonen map blocks
"Compression"            - ("Bytes after"+"Bytes for codebook")/"Bytes before"
"Error"                  - displays an error if any
