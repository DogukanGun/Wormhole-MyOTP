// SPDX-License-Identifier: GPL-3.0

pragma solidity >=0.8.2 <0.9.0;

import "./Authority.sol";
import "./interfaces/IAuthority.sol";
import "wormhole-solidity-sdk/interfaces/IWormholeRelayer.sol";
import "wormhole-solidity-sdk/interfaces/IWormholeReceiver.sol";


contract AuthorityManager is  IWormholeReceiver {
    
    IWormholeRelayer public immutable wormholeRelayer;

    constructor(address _wormholeRelayer)
       
    {
        wormholeRelayer = IWormholeRelayer(_wormholeRelayer);
    }

    struct Message{
        address sourceAddress;
        uint16 sourceChain;
    }

    mapping (address => address) authorities;
    mapping (address => Message) requestedVerification;
    uint256 constant GAS_LIMIT = 50_000;



    event VerificationRequested(address sender,uint256 challange);

    function requestVerification() public {
        require(authorities[msg.sender] != address(0),"Create a verification first");
        uint256 challange = IAuthority(authorities[msg.sender]).generateChallenge();
        emit VerificationRequested(msg.sender,challange);
    }

    function requestVerification(address userAddress,address source,uint16 sourceChain) private {
        require(authorities[userAddress] != address(0),"Create a verification first");
        require(authorities[userAddress] == address(0),"There is an active verification, please wait.");
        authorities[userAddress] = source;
        requestedVerification[userAddress] = Message(source,sourceChain);
        uint256 challange = IAuthority(authorities[userAddress]).generateChallenge();
        emit VerificationRequested(userAddress,challange);
    }

    function quoteCrossChainMessage(
        uint16 targetChain
    ) public view returns (uint256 cost) {
        (cost, ) = wormholeRelayer.quoteEVMDeliveryPrice(
            targetChain,
            0,
            GAS_LIMIT
        );
    }

    function sendMessage(string memory payloadStr,uint256 cost) private {
        bytes memory payload = abi.encode(payloadStr, msg.sender);
        wormholeRelayer.sendPayloadToEvm{value: cost}(
            requestedVerification[msg.sender].sourceChain,
            requestedVerification[msg.sender].sourceAddress,
            payload,
            0,
            GAS_LIMIT
        );
    }

    function verify(uint256 code) external payable {
        require(authorities[msg.sender] != address(0),"Create a verification first");
        uint256 cost = quoteCrossChainMessage(requestedVerification[msg.sender].sourceChain);
        require(msg.value == cost, "Incorrect payment"); 
        bool challangeResult = IAuthority(authorities[msg.sender]).verify(code);
        if(challangeResult) {
            sendMessage("verified",cost);
        }else{
            sendMessage("not_verified",cost);
        }
        authorities[msg.sender] = address(0);
    }

    function createVerification() public {
        address smartContractAddress = address(new Authority(address(this)));
        authorities[msg.sender] = smartContractAddress;
    }

    function createVerification(address userAddress) public {
        address smartContractAddress = address(new Authority(userAddress));
        authorities[msg.sender] = smartContractAddress;
    }

    function getVerificationContractAddress() external view returns(address){
        return authorities[msg.sender];
    }

    function bytesToString(bytes memory payload) public pure returns (string memory) {
        return string(payload);
    }

    function compareStrings(string memory str1, string memory str2) public pure returns (bool) {
        return keccak256(abi.encodePacked(str1)) == keccak256(abi.encodePacked(str2));
    }

    function bytesToAddress(bytes memory bys) public pure returns (address addr) {
        require(bys.length == 20, "Invalid length for an address");
        assembly {
            addr := mload(add(bys, 20))
        }
    }

    function bytes32ToAddress(bytes32 bys) public pure returns (address) {
        return address(uint160(uint256(bys)));
    }

    function receiveWormholeMessages(
        bytes memory payload,
        bytes[] memory additionalMessages,
        bytes32 sourceAddress,
        uint16 sourceChain,
        bytes32 deliveryHash
    ) external payable {
        string memory message = bytesToString(payload);
        address userAddress = bytesToAddress(additionalMessages[0]);
        address sAddress = bytes32ToAddress(sourceAddress);
        if(compareStrings(message,"request")){
            requestVerification(userAddress,sAddress,sourceChain);
        }else if(compareStrings(message,"create")){
            createVerification(userAddress);
        }
    }

}