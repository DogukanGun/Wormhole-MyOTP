// SPDX-License-Identifier: GPL-3.0

pragma solidity >=0.8.19 <0.9.0;

import "wormhole-solidity-sdk/interfaces/IWormholeRelayer.sol";
import "wormhole-solidity-sdk/interfaces/IWormholeReceiver.sol";


abstract contract CrossChainAuthority is IWormholeReceiver {

    address authorityManagerAddress;
    address public latestVerificationAddress;
    LatestVerification public latest ;

    struct LatestVerification {
        address VerificationAddress;
        bool    isVerified;
    }

    uint256 constant GAS_LIMIT = 50_000;

    IWormholeRelayer public immutable wormholeRelayer;

    event Verified(address userAddress);
    event NotVerified(address userAddress);

    constructor(address _authorityManagerAddress, address _wormholeRelayer ){
        _authorityManagerAddress = authorityManagerAddress;
        wormholeRelayer = IWormholeRelayer(_wormholeRelayer);
    }

    function bytesToString(bytes memory payload)
        public
        pure
        returns (string memory)
    {
        return string(payload);
    }

    function compareStrings(string memory str1, string memory str2)
        public
        pure
        returns (bool)
    {
        return
            keccak256(abi.encodePacked(str1)) ==
            keccak256(abi.encodePacked(str2));
    }

    function bytesToAddress(bytes memory bys)
        public
        pure
        returns (address addr)
    {
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
        //string memory message = bytesToString(payload);
        //user address must be taken
        (string memory comment, address sender) = abi.decode(
            payload,
            (string, address)
        );
        address sAddress = bytes32ToAddress(sourceAddress);
        latest.VerificationAddress = sAddress;
        if (compareStrings(comment, "verified")) {
            latest.isVerified = true;
            emit Verified(sAddress);
        } else if (compareStrings(comment, "not_verified")) {
            latest.isVerified = false;
            emit NotVerified(sAddress);
        }
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

    function sendCrossChainMessage(
        uint16 targetChain,
        address targetAddress
    ) public payable {
        uint256 cost = quoteCrossChainMessage(targetChain);
        require(msg.value == cost);
        wormholeRelayer.sendPayloadToEvm{value: cost}(
            targetChain,
            targetAddress,
            abi.encode("request", msg.sender), // payload
            0, // no receiver value needed since we're just passing a message
            GAS_LIMIT
        );
    }
}
